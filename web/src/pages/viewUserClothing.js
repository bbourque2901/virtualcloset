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
             this.bindClassMethods(['clientLoaded', 'mount', 'addClothingToPage', 'incrementWornCount'], this);
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
      document.getElementById('clothing').addEventListener("click", this.incrementWornCount);

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

        let clothingHtml = '<table id="clothing-table"><tr><th>Category</th><th>Color</th><th>Fit</th><th>Length</th><th>Occasion</th><th>Weather</th><th>Worn Count</th>';
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
                <td>${cloth.wornCount} <button data-id="${cloth.id}" class="button modify-wornCount">+</button></td>
            </tr>`;
        }

        document.getElementById('clothing').innerHTML = clothingHtml;

        document.getElementById('clothing-owner').innerText = cloth.customerId;
    }

    async incrementWornCount(e) {
        const incrementButton = e.target;
        if (!incrementButton.classList.contains('modify-wornCount')) {
            return;
        }
        
        incrementButton.innerText = "+..";

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const clothingId = incrementButton.getAttribute('data-id');

        try {
            const updatedClothing = await this.client.incrementClothingWC(clothingId);
            const row = document.getElementById(clothingId);
            const wornCountCell = row.cells[6];
            wornCountCell.innerHTML = `${updatedClothing.wornCount} <button data-id="${updatedClothing.id}" class="button modify-wornCount">+</button>`;
        } catch (error) {
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        }

        incrementButton.innerText = "+";
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