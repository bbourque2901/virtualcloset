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
        this.bindClassMethods(['clientLoaded', 'mount', 'addOutfitToPage', 'addClothingToPage', 'navigateToAddClothingPage', 'submitClothing'], this);
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
        if (outfitId) {
            document.getElementById('outfit-name').innerText = "Loading Outfit ...";
            const outfit = await this.client.getOutfit(outfitId);
            this.dataStore.set('outfit', outfit);
            const clothing = await this.client.getOutfitClothes(outfitId);
            this.dataStore.set('clothingItems', clothing);
        } else {
            console.error('Outfit ID is missing');
        }
    }

    mount() {
        this.header.addHeaderToPage();

        this.client = new VirtualClosetClient();
        this.clientLoaded();

        if (document.getElementById('add-clothing')) {
            document.getElementById('add-clothing').addEventListener('click', this.navigateToAddClothingPage);
        }

        if (document.getElementById('submit-clothing')) {
            document.getElementById('submit-clothing').addEventListener('click', this.submitClothing);
        }
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
     * Navigate to the add clothing page, including the outfit ID in the URL.
     */
    navigateToAddClothingPage() {
        const outfit = this.dataStore.get('outfit');
        if (outfit) {
            window.location.href = `addClothingToOutfit.html?id=${outfit.id}`;
        }
    }

    /**
     * Submit the clothing form and add the clothing item to the outfit.
     */
    async submitClothing(evt) {
        evt.preventDefault();
    
        const urlParams = new URLSearchParams(window.location.search);
        const outfitId = urlParams.get('id');
    
        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = '';
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
    
            const clothing = await this.client.createClothing(category, color, fit, length, occasion, weather);
    
            if (!clothing) {
                throw new Error('Failed to create clothing item');
            }
    
            const clothingList = await this.client.addClothingToOutfit(outfitId, clothing.clothingId);
    
            this.dataStore.set('clothingItems', clothingList);
            window.location.href = `outfit.html?id=${outfitId}`;
      
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        
            submitButton.innerText = origButtonText;
        
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

