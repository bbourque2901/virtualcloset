import VirtualClosetClient from '../api/virtualClosetClient';
import BindingClass from "../util/bindingClass";
import Header from '../components/header';
import DataStore from "../util/DataStore";

/**
 * Logic needed for the view user clothing page of the website.
 */
  class ViewUserClothing extends BindingClass {
     constructor() {
             super();
             this.bindClassMethods(['clientLoaded', 'mount', 'addClothingToPage'], this);
             this.dataStore = new DataStore();
             console.log("viewUserClothing constructor");
             this.header = new Header(this.dataStore);
     }

 /**
  * Once the client is loaded, get the clothing metadata.
  */
 async clientLoaded() {
     const urlParams = new URLSearchParams(window.location.search);
     const customerId = urlParams.get('email');
     document.getElementById('clothing').innerText = "Loading Clothing ...";
     const clothing = await this.client.getUserClothing(customerId);
     this.dataStore.set('clothing', clothing);
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
   * When the clothing is updated in the datastore, update the clothing metadata on the page.
   */
   addClothingToPage() {
        const clothing = this.dataStore.get('clothing');

        if (clothing == null) {
            return;
        }

        let clothingHtml = '<table id="clothing-table"><tr><th>Category</th><th>Color</th><th>Fit</th><th>Length</th><th>Occasion</th><th>Weather</th>';
        let cloth;
        for (cloth of clothing) {
            clothingHtml += `
            <tr id= "${cloth.id}">
                <td>${cloth.category || ''}</td>
                <td>${cloth.color || ''}</td>
                <td>${cloth.fit || ''}</td>
                <td>${cloth.length || ''}</td>
                <td>${cloth.occasion || ''}</td>
                <td>${cloth.weather || ''}</td>
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
        const viewuserclothing = new ViewUserClothing();
        viewuserclothing.mount();
  };

  window.addEventListener('DOMContentLoaded', main);