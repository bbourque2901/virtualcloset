import virtualClosetClient from '../api/virtualClosetClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

class CreateClothing extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'redirectToViewClothing'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.redirectToViewClothing);
        this.header = new Header(this.dataStore);
    }

    mount() {
        document.getElementById('create').addEventListener('click', this.submit);

        this.header.addHeaderToPage();

        this.client = new virtualClosetClient();
    }

    async submit(evt) {
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const createButton = document.getElementById('create');
        const origButtonText = createButton.innerText;
        createButton.innerText = 'Loading...';

        const category = document.getElementById('clothing-category').value;
        const color = document.getElementById('clothing-color').value;
        const fit = document.getElementById('clothing-fit').value;
        const length = document.getElementById('clothing-length').value;
        const occasion = document.getElementById('clothing-occasion').value;
        const weather = document.getElementById('clothing-weather').value;
        

        const clothing = await this.client.createClothing(category, color, fit, length, occasion, weather, (error) => {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('clothing', clothing);
    }

    redirectToViewClothing() {
        const clothing = this.dataStore.get('clothing');
        if (clothing != null) {
            window.location.href = `/clothing.html?id=${clothing.id}`;
        }
    }
}


const main = async () => {
    const createClothing = new CreateClothing();
    createClothing.mount();
};

window.addEventListener('DOMContentLoaded', main);
