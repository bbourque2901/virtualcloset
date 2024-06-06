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
        this.bindClassMethods(['clientLoaded', 'mount', 'addOutfitToPage', 'addClothingToPage', 'addClothingToOutfit'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addOutfitToPage);
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
    this.dataStore.set('clothing', clothing);
}

mount() {
    this.header.addHeaderToPage();

    this.client = new VirtualClosetClient();
    this.clientLoaded();

    document.getElementById('add-clothing').addEventListener('click', () => {
        window.location.href = 'addClothingToOutfit.html';
    });
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
    //document.getElementById('outfit-owner').innerText = outfit.customerName;

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
 * Method to run when the add clothing to outfit submit button is pressed. Call the VirtualClosetClient to add a clothing item to the
 * outfit.
 */
async addClothingToOutfit() {

    const errorMessageDisplay = document.getElementById('error-message');
    errorMessageDisplay.innerText = ``;
    errorMessageDisplay.classList.add('hidden');

    const outfit = this.dataStore.get('outfit');
    if (outfit == null) {
        return;
    }

    document.getElementById('add-clothing').innerText = 'Adding...';
    const category = document.getElementById('clothing-category').value;
    const color = document.getElementById('clothing-color').value;
    const fit = document.getElementById('clothing-fit').value;
    const length = document.getElementById('clothing-length').value;
    const occasion = document.getElementById('clothing-occasion').value;
    const weather = document.getElementById('clothing-weather').value;
    const outfitId = outfit.id;

    const clothingList = await this.client.addClothingToOutfit(outfitId, category, color, fit, length, occasion, weather, (error) => {
        errorMessageDisplay.innerText = `Error: ${error.message}`;
        errorMessageDisplay.classList.remove('hidden');           
    });

    this.dataStore.set('clothingItems', clothingList);

    document.getElementById('add-clothing').innerText = 'Add Clothing';
    document.getElementById("add-clothing-form").reset();
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