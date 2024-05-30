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
        this.bindClassMethods(['clientLoaded', 'mount', 'addOutfitToPage'], this);
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
    //document.getElementById('clothing').innerText = "(loading clothing...)";
   // const clothing = await this.client.getOutfitClothes(outfitId);
    //this.dataStore.set('clothing', clothing);
}

mount() {
    //document.getElementById('add-clothing').addEventListener('click', this.addClothing);

    this.header.addHeaderToPage();

    this.client = new VirtualClosetClient();
    this.clientLoaded();
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

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const viewOutfit = new ViewOutfit();
    viewOutfit.mount();
};

window.addEventListener('DOMContentLoaded', main);