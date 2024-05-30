import virtualClosetClient from '../api/virtualClosetClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

class CreateOutfit extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'redirectToViewOutfit'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.redirectToViewOutfit);
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

        const outfitName = document.getElementById('outfit-name').value;
        const tagsText = document.getElementById('tags').value;

        let tags;
        if (tagsText.length < 1) {
            tags = null;
        } else {
            tags = tagsText.split(/\s*,\s*/);
        }

        const outfit = await this.client.createOutfit(outfitName, tags, (error) => {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('outfit', outfit);
    }

    redirectToViewOutfit() {
        const outfit = this.dataStore.get('outfit');
        if (outfit != null) {
            window.location.href = `/outfit.html?id=${outfit.id}`;
        }
    }
}


const main = async () => {
    const createOutfit = new CreateOutfit();
    createOutfit.mount();
};

window.addEventListener('DOMContentLoaded', main);
