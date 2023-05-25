import axios from "axios";

export async function getFavorites(user: any) {
	let listings = [];
	if (user) {
		return axios
			.post("http://127.0.0.1:3000/api/favorites", {
				customHeaders: user.customHeaders,
				userId: user.id,
				method: "GET",
			})
			.then((response) => {
				listings = response.data;
				return listings;
			})
			.catch((error) => {
				console.log(error);
			});
	}
}

export async function addToFavorites(listingId: string, user: any) {
	let listings = [];
	if (user) {
		return axios
			.post("http://127.0.0.1:3000/api/favorites", {
				customHeaders: user.customHeaders,
				userId: user.id,
				listingId,
				method: "POST",
			})
			.then((response) => {
				listings = response.data;
				return listings;
			})
			.catch((error) => {
				console.log(error);
			});
	}
}

export async function removeFromFavorites(listingId: string, user: any) {
	let listings = [];
	if (user) {
		return axios
			.post("http://127.0.0.1:3000/api/favorites", {
				customHeaders: user.customHeaders,
				userId: user.id,
				listingId,
				method: "DELETE",
			})
			.then((response) => {
				listings = response.data;
				return listings;
			})
			.catch((error) => {
				console.log(error);
			});
	}
}
