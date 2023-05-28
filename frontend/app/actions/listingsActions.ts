import axios from "axios";
import getCurrentUser from "./getCurrentUser";

export interface IListingsParams {
	userId?: string;
	guestCount?: number;
	roomCount?: number;
	bathroomCount?: number;
	startDate?: string;
	endDate?: string;
	location?: string;
	category?: string;
}

export async function getListings(params: IListingsParams) {
	const {
		userId,
		roomCount,
		guestCount,
		bathroomCount,
		location,
		startDate,
		endDate,
		category,
	} = params;

	let query: any = {};

	if (userId) {
		query.userId = userId;
	}

	if (category) {
		query.category = category;
	}

	if (roomCount) {
		query.roomCount = roomCount;
	}

	if (guestCount) {
		query.guestCount = guestCount;
	}

	if (bathroomCount) {
		query.bathroomCount = bathroomCount;
	}

	if (location) {
		query.location = location;
	}

	if (startDate && endDate) {
		const startISO = new Date(startDate).toISOString();
		const endISO = new Date(endDate).toISOString();

		query.startDate = startISO;
		query.endDate = endISO;
	}

	const user = await getCurrentUser();
	let listings = [];
	if (user) {
		// Check if query is empty
		if (Object.keys(query).length === 0) {
			return axios
				.post(`${process.env.FRONTEND_URL}/api/listings`, {
					customHeaders: user.customHeaders,
				})
				.then((response) => {
					listings = response.data;
					return listings;
				})
				.catch((error) => {
					console.log(error);
				});
		} else {
			return axios
				.post(`${process.env.FRONTEND_URL}/api/listings`, {
					customHeaders: user.customHeaders,
					query,
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
}

export async function getListingById(id: string) {
	const user = await getCurrentUser();
	let listings = [];
	if (user) {
		return axios
			.post(`${process.env.FRONTEND_URL}/api/listings`, {
				customHeaders: user.customHeaders,
				id: id,
			})
			.then((response) => {
				listings = response.data;
				console.log(listings, "listingsDataActions");
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
			.post(`${process.env.FRONTEND_URL}/api/listings`, {
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
