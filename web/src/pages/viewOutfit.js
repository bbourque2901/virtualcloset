import VirtualClosetClient from '../api/virtualClosetClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the view outfit page of the website.
 */
class ViewOutfit extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addOutfitToPage', 'addClothingToPage', 'submitClothing', 'redirectToViewOutfit'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addOutfitToPage);
        this.dataStore.addChangeListener(this.addClothingToPage);
        this.header = new Header(this.dataStore);
        console.log("viewoutfit constructor");
    }

    /**
     * Once the client is loaded, get the outfit metadata and clothing list.
     */
    async clientLoaded() {
        const urlParams = new URLSearchParams(window.location.search);
        const outfitId = urlParams.get('id');
        document.getElementById('outfit-name').innerText = "Loading Outfit ...";
        const outfit = await this.client.getOutfit(outfitId);
        this.dataStore.set('outfit', outfit);
        document.getElementById('clothing').innerText = "(loading clothing...)";
        const clothing = await this.client.getOutfitClothes(outfitId);
        this.dataStore.set('clothingItems', clothing);
    }

    mount() {
        this.header.addHeaderToPage();

        this.client = new VirtualClosetClient();
        this.clientLoaded();

        document.getElementById('add-clothing').addEventListener('click', this.submitClothing);
    }

    /**
     * When the outfit is updated in the datastore, update the outfit metadata on the page.
     */
    addOutfitToPage() {
        const outfit = this.dataStore.get('outfit');
        if (outfit == null) {
            return;
        }

        document.getElementById('outfit-name').innerText = outfit.name;

        let tagHtml = '';
        let tag;
        for (tag of outfit.tags) {
            tagHtml += '<div class="tag">' + tag + '</div>';
        }
        document.getElementById('tags').innerHTML = tagHtml;
    }

    /**
     * When the clothing items are updated in the datastore, update the list of clothing items on the page.
     */
    addClothingToPage() {
        const clothingItems = this.dataStore.get('clothingItems');

        if (clothingItems == null) {
            return;
        }

        let clothingHtml = '';
        let item;
        for (item of clothingItems) {
            clothingHtml += `
                <li class="clothing-item">
                    <span class="category">${item.category}</span>
                    <span class="color">${item.color}</span>
                    <span class="fit">${item.fit}</span>
                    <span class="length">${item.length}</span>
                    <span class="occasion">${item.occasion}</span>
                    <span class="weather">${item.weather}</span>
                </li>
            `;
        }
        document.getElementById('clothing-items').innerHTML = clothingHtml;
    }

    /**
     * Handle the form submission to create a new clothing item and add it to an outfit.
     */
    async submitClothing(evt) {
       // evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = '';
        errorMessageDisplay.classList.add('hidden');

        const submitButton = document.getElementById('add-clothing');
        const origButtonText = submitButton.innerText;
        submitButton.innerText = 'Loading...';

        const category = document.getElementById('clothing-category').value;
        const color = document.getElementById('clothing-color').value;
        const fit = document.getElementById('clothing-fit').value;
        const length = document.getElementById('clothing-length').value;
        const occasion = document.getElementById('clothing-occasion').value;
        const weather = document.getElementById('clothing-weather').value;

        const outfit = this.dataStore.get('outfit');
        if (!outfit) {
            errorMessageDisplay.innerText = 'Error: Outfit not found.';
            errorMessageDisplay.classList.remove('hidden');
            submitButton.innerText = origButtonText;
            return;
        }

        const outfitId = outfit.id;
        const customerId = outfit.customerId; 

        try {
            const createClothingRequest = {
                customerId: customerId,
                category: category,
                color: color,
                fit: fit,
                length: length,
                occasion: occasion,
                weather: weather
            };
            const clothing = await this.client.createClothing(createClothingRequest);
            if (!clothing || !clothing.clothingId) {
                throw new Error('Failed to create clothing item');
            }

            const clothingList = await this.client.addClothingToOutfit(outfitId, clothing.clothingId, (error) => {
                errorMessageDisplay.innerText = `Error: ${error.message}`;
                errorMessageDisplay.classList.remove('hidden');           
            });

            this.dataStore.set('clothingItems', clothingList);
        } catch (error) {
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
            submitButton.innerText = origButtonText;
            return;
        }

        submitButton.innerText = origButtonText;
        this.redirectToViewOutfit();
    }

    /**
     * Redirect to the view outfit page.
     */
    redirectToViewOutfit() {
        const outfit = this.dataStore.get('outfit');
        if (outfit) {
            window.location.href = `viewOutfit.html?id=${outfit.id}`;
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const viewOutfit = new ViewOutfit();
    viewOutfit.mount();
};

window.addEventListener('DOMContentLoaded', main);


