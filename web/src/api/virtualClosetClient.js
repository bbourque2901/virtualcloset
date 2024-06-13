import axios from "axios";
import BindingClass from "../util/bindingClass";
import Authenticator from "./authenticator";

/**
 * Client to call the VirtualCloset.
 */

export default class virtualClosetClient extends BindingClass {

    constructor(props = {}) {
        super();

        const methodsToBind = ['clientLoaded', 'getIdentity', 'login', 'logout', 'getOutfit', 'createOutfit', 'getClothing', 'updateOutfitName',
             'createClothing', 'getOutfitClothes', 'addClothingToOutfit', 'getUserOutfits', 'removeOutfit', 'removeClothingFromOutfit',
            'getSortedClothing', 'getSortedOutfit', 'incrementClothingWC', 'incrementOutfitWC'];
        this.bindClassMethods(methodsToBind, this);

        this.authenticator = new Authenticator();;
        this.props = props;

        axios.defaults.baseURL = process.env.API_BASE_URL;
        this.axiosClient = axios;
        this.clientLoaded();
    }

    clientLoaded() {
        if (this.props.hasOwnProperty("onReady")) {
            this.props.onReady(this);
        }
    }

    async getIdentity(errorCallback) {
        try {
            const isLoggedIn = await this.authenticator.isUserLoggedIn();

            if (!isLoggedIn) {
                return undefined;
            }

            return await this.authenticator.getCurrentUserInfo();
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async login() {
        this.authenticator.login();
    }

    async logout() {
        this.authenticator.logout();
    }

    async getTokenOrThrow(unauthenticatedErrorMessage) {
        const isLoggedIn = await this.authenticator.isUserLoggedIn();
        if (!isLoggedIn) {
            throw new Error(unauthenticatedErrorMessage);
        }

        return await this.authenticator.getUserToken();
    }

    /**
     * Gets the outfit for the given ID.
     * @param id Unique identifier for an outfit
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The outfit's metadata.
     */
    async getOutfit(id, errorCallback) {
        try {
            const response = await this.axiosClient.get(`outfits/${id}`);
            return response.data.outfit;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Create a new outfit owned by the current user.
     * @param name The name of the outfit to create.
     * @param tags Metadata tags to associate with an outfit.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The outfit that has been created.
     */
    async createOutfit(name, tags, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can create outfits.");
            const response = await this.axiosClient.post(`outfits`, {
                name: name,
                tags: tags
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.outfit;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Create a new clothing item owned by the current user.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The clothing item that has been created.
     */
    async createClothing(category, color, fit, length, occasion, weather, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can create clothing.");
            const response = await this.axiosClient.post(`clothing`, {
                category: category,
                color: color, 
                fit: fit, 
                length: length,
                occasion: occasion,
                weather: weather
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.clothing;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Gets the clothing item for the given clothingID.
     * @param clothingId Unique identifier for an outfit
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The clothing's metadata.
     */
    async getClothing(clothingId, errorCallback) {
        try {
            const response = await this.axiosClient.get(`clothing/${clothingId}`);
            return response.data.clothing;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Get the clothing items on a given outfit by the outfit's identifier.
     * @param id Unique identifier for an outfit
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The list of clothes in an outfit.
     */
    async getOutfitClothes(id, errorCallback) {
        try {
            const response = await this.axiosClient.get(`outfits/${id}/clothing`);
            return response.data.clothingList;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Add clothing to an outfit.
     * @param id The id of the outfit to add clothing to.
     * @param clothingId the clothingId of the clothing item being added
     * @returns The list of clothing in an outfit.
     */
    async addClothingToOutfit(id, clothingId,errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can add clothing to an outfit.");
            const response = await this.axiosClient.post(`outfits/${id}/clothing`, {
                id: id,
                clothingId: clothingId
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.clothingList;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Get the outfits of a given user.
     * @param customerId Unique identifier for a user
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The list of outfits associated with a user.
     */
    async getUserOutfits(customerId, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can view their outfits.");
            const response = await this.axiosClient.get(`userOutfits`, {
                headers: {
                    Authorization: `Bearer ${token}`
                  }
                });
            return response.data.outfits;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Get the clothes of a given user.
     * @param customerId Unique identifier for a user
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The list of clothing associated with a user.
     */
    async getUserClothing(customerId, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can view their clothes.");
            const response = await this.axiosClient.get(`userClothing`, {
                headers: {
                    Authorization: `Bearer ${token}`
                  }
                });
            return response.data.clothing;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

     /**
          * removes an outfit.
          * @param id The id of the outfit.
          * @returns The list of clothing items in an outfit.
          */
     async removeOutfit(id, errorCallback) {
        try {
            console.log('delete endpoint called with id ' + id);
            const token = await this.getTokenOrThrow("Only authenticated users can remove an outfit.");
            const response = await this.axiosClient.delete(`outfits/${id}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                  },
                  data: {
                    id: id
                  }
                });
            return response.data.outfits;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * removes a clothing item from an outfit.
     * @param id The id of the outfit to remove the clothing item from.
     * @param clothingId The clothingId that uniquely identifies the clothing item.
     * @returns The list of clothing items in an outfit.
     */
    async removeClothingFromOutfit(id, clothingId, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can remove a clothing item from an outfit.");
            const response = await this.axiosClient.delete(`outfits/${id}/clothing/${clothingId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                  },
                  data: {
                    id: id,
                    clothingId: clothingId
                  }
                });
            return response.data.clothing;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Update outfit name.
     * @param id the id of the outfit.
     * @returns an updated outfit name.
     */
    async updateOutfitName(outfitId, newName, errorCallback) {
        try {
                const token = await this.getTokenOrThrow("Only authenticated users can update an outfit name.");
                const response = await this.axiosClient.put(`outfits/${outfitId}`, {
                    id: outfitId,
                    name: newName
                }, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    },
                });
                return response.data.outfits;
            } catch (error) {
                this.handleError(error, errorCallback)
            }
     }

     /**
     * Get the sorted clothes of a given user.
     * @param customerId Unique identifier for a user
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The list of sorted clothing associated with a user.
     */
     async getSortedClothing(customerId, ascending, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can view their sorted clothes.");
            const response = await this.axiosClient.get(`sortedClothing`, {
                headers: {
                    Authorization: `Bearer ${token}`
                  },
                  params: {
                    ascending: ascending
                  }
                });
            return response.data.clothes;
        } catch (error) {
            this.handleError(error, errorCallback);
        }
    }

      /**
     * Get the sorted outfits of a given user.
     * @param customerId Unique identifier for a user
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The list of sorted outfits associated with a user.
     */
      async getSortedOutfit(customerId, ascending, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can view their sorted outfits.");
            const response = await this.axiosClient.get(`sortedOutfits`,  {
                headers: {
                    Authorization: `Bearer ${token}`
                  },
                  params: {
                    ascending: ascending
                  }
                });
            return response.data.outfits;
        } catch (error) {
            this.handleError(error, errorCallback);
        }
    }

    /**
     * Update worncount of an outfit.
     * @param id The id of the outfit to update.
     * @returns The updated outfit.
     */
    async incrementOutfitWC(id, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can modify the worncount.");
            const response = await this.axiosClient.post(`outfits/${id}/wornCount`, {
                id: id,
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.oufit;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Update worncount of a clothing item.
     * @param id The id of the clothing item to update.
     * @returns The updated clothing item.
     */
    async incrementClothingWC(clothingId, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can modify the worncount.");
            const response = await this.axiosClient.post(`clothing/${clothingId}/wornCount`, {
                clothingId: clothingId,
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.clothing;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }


    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    handleError(error, errorCallback) {
        console.error(error);

        const errorFromApi = error?.response?.data?.error_message;
        if (errorFromApi) {
            console.error(errorFromApi)
            error.message = errorFromApi;
        }

        if (errorCallback) {
            errorCallback(error);
        }
    }
}