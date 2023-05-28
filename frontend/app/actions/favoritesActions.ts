import axios from "axios";

export async function getFavorites(user: any) {
	let listings = [];

	console.log("siemano", process.env.FRONTEND_URL);
	if (user) {
		return axios
			.post(`${process.env.FRONTEND_URL}/api/favorites`, {
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
			.post(`${process.env.FRONTEND_URL}/api/favorites`, {
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
			.post(`${process.env.FRONTEND_URL}/api/favorites`, {
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
