import VirtualClosetClient from '../api/virtualClosetClient';
import BindingClass from "../util/bindingClass";
import Header from '../components/header';
import DataStore from "../util/DataStore";

/**
 * Logic needed for the view user outfits page of the website.
 */
  class ViewUserOutfits extends BindingClass {
     constructor() {
             super();
             this.bindClassMethods(['clientLoaded', 'mount', 'addOutfitsToPage', 'remove', 'incrementWornCount'], this);
             this.dataStore = new DataStore();
             console.log("viewUserOutfits constructor");
             this.header = new Header(this.dataStore);
     }

 /**
  * Once the client is loaded, get the outfit metadata.
  */
 async clientLoaded() {
     const urlParams = new URLSearchParams(window.location.search);
     const customerId = urlParams.get('email');
     document.getElementById('outfits').innerText = "Loading Lists ...";
     const outfits = await this.client.getUserOutfits(customerId);
     this.dataStore.set('outfits', outfits);
     this.addOutfitsToPage();
 }

 /**
  * Load the VirtualClosetClient.
  */
  mount() {
      document.getElementById('outfits').addEventListener("click", this.remove);
      document.getElementById('outfits').addEventListener("click", this.incrementWornCount);

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

        let outfitsHtml = '<table id="outfits-table"><tr><th>Name</th><th>Tags</th><th>Worn Count</th><th>Remove Outfit</th></tr>';
        let outfit;
        for (outfit of outfits) {
            outfitsHtml += `
            <tr id= "${outfit.id}">
                <td>
                    <a href="outfit.html?id=${outfit.id}">${outfit.name}</a>
                </td>
                <td>${outfit.tags?.join(', ') || ''}</td>
                <td>${outfit.wornCount} <button data-id="${outfit.id}" class="button modify-wornCount">+</button></td>
                <td><button data-id="${outfit.id}" class="button remove-outfit">Remove ${outfit.name}</button></td>
            </tr>`;
        }

        document.getElementById('outfits').innerHTML = outfitsHtml;

        document.getElementById('outfit-owner').innerText = outfit.customerId;
    }

    /**
     * when remove button is clicked, removes outfit.
     */
     async remove(e) {
         const removeButton = e.target;
         if (!removeButton.classList.contains('remove-outfit')) {
              return;
         }

         removeButton.innerText = "Removing...";

         const errorMessageDisplay = document.getElementById('error-message');
         errorMessageDisplay.innerText = ``;
         errorMessageDisplay.classList.add('hidden');

         await this.client.removeOutfit(removeButton.dataset.id, (error) => {
           errorMessageDisplay.innerText = `Error: ${error.message}`;
           errorMessageDisplay.classList.remove('hidden');
         });

         document.getElementById(removeButton.dataset.id).remove()
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

        const outfitId = incrementButton.getAttribute('data-id');

        try {
            const updatedOutfit = await this.client.incrementOutfitWC(outfitId);
            const row = document.getElementById(outfitId);
            const wornCountCell = row.cells[2];
            wornCountCell.innerHTML = `${updatedOutfit.wornCount} <button data-id="${updatedOutfit.id}" class="button modify-wornCount">+</button>`;
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
        const viewuseroutfits = new ViewUserOutfits();
        viewuseroutfits.mount();
  };

  window.addEventListener('DOMContentLoaded', main);