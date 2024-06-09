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
        this.bindClassMethods(['clientLoaded', 'mount', 'addOutfitToPage', 'updateOutfitName',
             'addClothingToPage', 'navigateToAddClothingPage', 'submitClothing', 'remove'], this);
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
        
        if (document.getElementById('clothing-items')) {
        document.getElementById('clothing-items').addEventListener('click', this.remove);
        }

        if (document.getElementById('add-clothing')) {
            document.getElementById('add-clothing').addEventListener('click', this.navigateToAddClothingPage);
        }

        if (document.getElementById('submit-clothing')) {
            document.getElementById('submit-clothing').addEventListener('click', this.submitClothing);
        }
        
        if (document.getElementById('update-outfit')) {
        document.getElementById('update-outfit').addEventListener('click', this.updateOutfitName);
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
        const outfit = this.dataStore.get('outfit');
    
        if (clothingItems == null) {
            return;
        }
    
        let clothingHtml = '<table id="clothing-table"><tr><th>Category</th><th>Color</th><th>Fit</th><th>Length</th><th>Occasion</th><th>Weather</th><th>Remove Clothing Item</th>';
        let item;
        for (item of clothingItems) {
            clothingHtml += `
            <tr id="${item.clothingId + outfit.id}">
                <td>${item.category}</td>
                <td>${item.color}</td>
                <td>${item.fit}</td>
                <td>${item.length}</td>
                <td>${item.occasion}</td>
                <td>${item.weather}</td>
                <td><button data-clothing-Id="${item.clothingId}" data-outfit-id="${outfit.id}" class="button remove-clothing">Remove</button></td>
            </tr>`;
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

    /**
          * when remove button is clicked, removes clothing item from outfit.
          */
    async remove(e) {
        const removeButton = e.target;
        if (!removeButton.classList.contains("remove-clothing")) {
            return;
        }

        console.log('Outfit ID:', removeButton.dataset.outfitId);
        console.log('Clothing ID:', removeButton.dataset.clothingId);

        removeButton.innerText = "Removing...";

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        await this.client.removeClothingFromOutfit(removeButton.dataset.outfitId, removeButton.dataset.clothingId, (error) => {
           errorMessageDisplay.innerText = `Error: ${error.message}`;
           errorMessageDisplay.classList.remove('hidden');
       });

        document.getElementById(removeButton.dataset.clothingId + removeButton.dataset.outfitId).remove();
  }

  /**
          * when button is clicked, user is prompted to update outfit name.
          */
  async updateOutfitName() {
    const errorMessageDisplay = document.getElementById('error-message');
    errorMessageDisplay.innerText = ``;
    errorMessageDisplay.classList.add('hidden');

    const newName = prompt("Enter new outfit name: ");
    if (!newName) return;

    const outfit = this.dataStore.get('outfit');
    if (outfit == null) {
      return;
    }

    document.getElementById('outfit-name').innerText = 'Updating...';

    const newOutfit = await this.client.updateOutfitName(outfit.id, newName, (error) => {
      errorMessageDisplay.innerText = `Error: ${error.message}`;
      errorMessageDisplay.classList.remove('hidden');
    });

    document.getElementById('outfit-name').innerText = newName;
    this.dataStore.set('outfit', newOutfit);
    console.log("button clicked!");
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

