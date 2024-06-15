import VirtualClosetClient from '../api/virtualClosetClient';
import BindingClass from "../util/bindingClass";
import Header from '../components/header';
import DataStore from "../util/DataStore";

/**
 * Logic needed for the view user outfits page of the website.
 */
class ViewUserRecommendations extends BindingClass {
    constructor() {
            super();
            this.bindClassMethods(['clientLoaded', 'mount', 'addOutfitsToPage', 'addClothingToPage'], this);
            this.dataStore = new DataStore();
            console.log("viewUserRecommendations constructor");
            this.header = new Header(this.dataStore);
    }

    /**
  * Once the client is loaded, get the outfit metadata and clothing list.
  */
 async clientLoaded() {
    const urlParams = new URLSearchParams(window.location.search);
    const customerId = urlParams.get('email');
    const ascending = true;
    document.getElementById('outfits').innerText = "Loading Outfits ...";
    document.getElementById('clothing').innerText = "Loading Clothes ...";
    const outfits = await this.client.getSortedOutfit(customerId, ascending);
    const clothing = await this.client.getSortedClothing(customerId, ascending);
    this.dataStore.set('outfits', outfits);
    this.dataStore.set('clothing', clothing);
    this.addOutfitsToPage();
    this.addClothingToPage();
}

/**
  * Load the VirtualClosetClient.
  */
mount() {
    this.client = new VirtualClosetClient();
    this.clientLoaded();
    this.header.addHeaderToPage();
}

/**
   * When the outfit is updated in the datastore, update the outfit metadata on the page.
   */
addOutfitsToPage() {
    const outfits = this.dataStore.get('outfits');

    if (outfits == null) {
        return;
    }

    const limit = 5; 
        let outfitsHtml = '<table id="outfit-worncount-index"><tr><th>Name</th><th>Tags</th><th>Worn Count</th></tr>';
        let outfit;
        for (let i = 0; i < Math.min(outfits.length, limit); i++) {
            outfit = outfits[i];
            outfitsHtml += `
            <tr id="${outfit.id}">
                <td>
                    <a href="outfit.html?id=${outfit.id}">${outfit.name}</a>
                </td>
                <td>${outfit.tags?.join(', ') || ''}</td>
                <td>${outfit.wornCount}</td>
            </tr>`;
        }

    document.getElementById('outfits').innerHTML = outfitsHtml;

    document.getElementById('outfit-owner').innerText = outfit.customerId;
}

/**
   * When the clothing is updated in the datastore, update the clothing metadata on the page.
   */
addClothingToPage() {
    const clothing = this.dataStore.get('clothing');

    if (clothing == null) {
        return;
    }

    const limit = 5; 
        let clothingHtml = '<table id="clothing-worncount-index"><tr><th>Category</th><th>Color</th><th>Fit</th><th>Length</th><th>Occasion</th><th>Weather</th><th>Worn Count</th></tr>';
        let cloth;
        for (let i = 0; i < Math.min(clothing.length, limit); i++) {
            cloth = clothing[i];
            clothingHtml += `
            <tr id="${cloth.id}">
                <td>${cloth.category || ''}</td>
                <td>${cloth.color || ''}</td>
                <td>${cloth.fit || ''}</td>
                <td>${cloth.length || ''}</td>
                <td>${cloth.occasion || ''}</td>
                <td>${cloth.weather || ''}</td>
                <td>${cloth.wornCount}</td>
            </tr>`;
        }

    document.getElementById('clothing').innerHTML = clothingHtml;

    document.getElementById('clothing-owner').innerText = cloth.customerId;
}

}

 /**
  * Main method to run when the page contents have loaded.
  */
 const main = async () => {
    const viewUserRecommendations = new ViewUserRecommendations();
    viewUserRecommendations.mount();
};

window.addEventListener('DOMContentLoaded', main);