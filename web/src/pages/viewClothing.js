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
        this.bindClassMethods(['clientLoaded', 'mount', 'addClothingToPage'], this);
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
    const clothingId = urlParams.get('id');
    document.getElementById('clothing-id').innerText = "Loading Clothing Item ...";
    const clothing = await this.client.getClothing(clothingId);
    this.dataStore.set('clothing', clothing);
}

mount() {

    this.header.addHeaderToPage();

    this.client = new VirtualClosetClient();
    this.clientLoaded();
}

/**
     * When the clothing is updated in the datastore, update the clothing metadata on the page.
     */
addClothingToPage() {
    const clothing = this.dataStore.get('clothing');
    if (clothing == null) {
        return;
    }

    document.getElementById('clothing-id').innerText = clothing.clothingId;
    document.getElementById('tags').innerHTML = tagHtml;
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