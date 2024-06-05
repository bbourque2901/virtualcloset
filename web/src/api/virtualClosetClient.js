import axios from "axios";
import BindingClass from "../util/bindingClass";
import Authenticator from "./authenticator";

/**
 * Client to call the VirtualCloset.
 */

export default class virtualClosetClient extends BindingClass {

    constructor(props = {}) {
        super();

        const methodsToBind = ['clientLoaded', 'getIdentity', 'login', 'logout', 'getOutfit', 'createOutfit'];
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