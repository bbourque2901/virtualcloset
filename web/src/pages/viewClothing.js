import VirtualClosetClient from '../api/virtualClosetClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the view outfit page of the website.
 */
class ViewClothing extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addClothingToPage', 'redirectToAllClothing'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addClothingToPage);
        this.header = new Header(this.dataStore);
        console.log("viewclothing constructor");
    }

 /**
     * Once the client is loaded, get the outfit metadata and clothing list.
     */
 async clientLoaded() {
    const urlParams = new URLSearchParams(window.location.search);
    const clothingId = urlParams.get('clothingId');
    document.getElementById('title').innerText = "Loading Clothing Item ...";
    const clothing = await this.client.getClothing(clothingId);
    this.dataStore.set('clothing', clothing);
}

mount() {

    this.header.addHeaderToPage();

    this.client = new VirtualClosetClient();
    this.clientLoaded();
    document.getElementById('view-all-clothing').addEventListener('click', this.redirectToAllClothing);
}

/**
     * When the clothing is updated in the datastore, update the clothing metadata on the page.
     */
addClothingToPage() {
    const clothing = this.dataStore.get('clothing');
    if (clothing == null) {
        return;
    }

    document.getElementById('title').innerText = `${clothing.category} - ${clothing.color}`;
    document.getElementById('clothing-category').innerText = clothing.category;
    document.getElementById('clothing-color').innerText = clothing.color;
    document.getElementById('clothing-fit').innerText = clothing.fit;
    document.getElementById('clothing-length').innerText = clothing.length;
    document.getElementById('clothing-occasion').innerText = clothing.occasion;
    document.getElementById('clothing-weather').innerText = clothing.weather;
}

/**
     * Redirect to the all clothing items page.
     */
redirectToAllClothing() {
    window.location.href = '/userClothing.html';
}

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const viewClothing = new ViewClothing();
    viewClothing.mount();
};

window.addEventListener('DOMContentLoaded', main);