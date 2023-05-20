import axios from "axios";
import getCurrentUser from "./getCurrentUser";

export async function getListings() {
	const user = await getCurrentUser();
	let listings = [];
	if (user) {
		return axios
			.post("http://localhost:3000/api/listings", {
				customHeaders: user.customHeaders,
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

export async function getListingById(id: string) {
	const user = await getCurrentUser();
	let listings = [];
	if (user) {
		return axios
			.post("http://localhost:3000/api/listings", {
				customHeaders: user.customHeaders,
				id: id,
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

export async function getListingsByUserId(userId: string) {
	const user = await getCurrentUser();
	let listings = [];
	if (user) {
		return axios
			.post("http://localhost:3000/api/listings", {
				customHeaders: user.customHeaders,
				userId,
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
