import axios from "axios";

export async function getFavorites(userId: string, user: any) {
	let listings = [];
	if (user) {
		return axios
			.post("http://localhost:3000/api/favorites", {
				customHeaders: user.customHeaders,
				userId,
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

export async function addToFavorites(
	userId: string,
	listingId: string,
	user: any
) {
	let listings = [];
	if (user) {
		return axios
			.post("http://localhost:3000/api/favorites", {
				customHeaders: user.customHeaders,
				userId,
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

export async function removeFromFavorites(
	userId: string,
	listingId: string,
	user: any
) {
	let listings = [];
	if (user) {
		return axios
			.post("http://localhost:3000/api/favorites", {
				customHeaders: user.customHeaders,
				userId,
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
