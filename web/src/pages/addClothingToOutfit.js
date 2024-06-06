import VirtualClosetClient from '../api/virtualClosetClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

class AddClothingToOutfit extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'redirectToViewOutfit'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.redirectToViewOutfit);
        this.header = new Header(this.dataStore);
    }

    mount() {
        document.getElementById('submit-clothing').addEventListener('click', this.submit);
        this.header.addHeaderToPage();
        this.client = new VirtualClosetClient();
    }

    async submit(evt) {
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const submitButton = document.getElementById('submit-clothing');
        const origButtonText = submitButton.innerText;
        submitButton.innerText = 'Loading...';

        const category = document.getElementById('clothing-category').value;
        const color = document.getElementById('clothing-color').value;
        const fit = document.getElementById('clothing-fit').value;
        const length = document.getElementById('clothing-length').value;
        const occasion = document.getElementById('clothing-occasion').value;
        const weather = document.getElementById('clothing-weather').value;

        const urlParams = new URLSearchParams(window.location.search);
        const outfitId = urlParams.get('id');

        if (!outfitId) {
            errorMessageDisplay.innerText = 'Error: Outfit ID not found.';
            errorMessageDisplay.classList.remove('hidden');
            submitButton.innerText = origButtonText;
            return;
        }

        try {
            // Create the clothing item
            const clothing = await this.client.createClothing(category, color, fit, length, occasion, weather);
            if (!clothing) {
                throw new Error('Failed to create clothing item');
            }

            // Add the created clothing item to the outfit
            await this.client.addClothingToOutfit(outfitId, clothing.clothingId);

            this.dataStore.set('clothing', clothing);
            this.dataStore.set('outfitId', outfitId);
        } catch (error) {
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
            submitButton.innerText = origButtonText;
        }
    }

    redirectToViewOutfit() {
        const outfitId = this.dataStore.get('outfitId');
        if (outfitId) {
            window.location.href = `viewOutfit.html?id=${outfitId}`;
        }
    }
}

const main = async () => {
    const addClothing = new AddClothingToOutfit();
    addClothing.mount();
};

window.addEventListener('DOMContentLoaded', main);
