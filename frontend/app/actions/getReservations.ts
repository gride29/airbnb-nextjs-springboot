import axios from "axios";
import getCurrentUser from "./getCurrentUser";

interface IParams {
	listingId?: string;
	userId?: string;
	authorId?: string;
}

export async function getReservationsByListingId(listingId: string) {
	const user = await getCurrentUser();
	let reservations = [];
	if (user) {
		return axios
			.post("http://localhost:3000/api/reservations", {
				customHeaders: user.customHeaders,
				listingId,
			})
			.then((response) => {
				reservations = response.data;
				return reservations;
			})
			.catch((error) => {
				console.log(error);
			});
	}
}

export async function getReservationsByUserId(userId: string) {
	const user = await getCurrentUser();
	let reservations = [];
	if (user) {
		return axios
			.post("http://localhost:3000/api/reservations", {
				customHeaders: user.customHeaders,
				userId,
			})
			.then((response) => {
				reservations = response.data;
				return reservations;
			})
			.catch((error) => {
				console.log(error);
			});
	}
}

export default async function getReservations(params: IParams) {
	try {
		const { listingId, userId, authorId } = params;

		const query: any = {};

		let reservationsData = [];

		if (listingId) {
			reservationsData = await getReservationsByListingId(listingId);
		}

		if (userId) {
			reservationsData = await getReservationsByUserId(userId);
		}

		if (authorId) {
			// query.listing = { userId: authorId };
		}

		return reservationsData;
	} catch (error: any) {
		throw new Error(error);
	}
}
