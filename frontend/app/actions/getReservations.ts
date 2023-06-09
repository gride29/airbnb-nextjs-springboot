import axios from "axios";
import getCurrentUser from "./getCurrentUser";

interface IParams {
	listingId?: string;
	userId?: string;
	ownerId?: string;
}

export async function getAllReservations() {
	const user = await getCurrentUser();
	let reservations = [];
	if (user) {
		return axios
			.post(`${process.env.FRONTEND_URL}/api/reservations`, {
				customHeaders: user.customHeaders,
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

export async function getReservationsByListingId(listingId: string) {
	const user = await getCurrentUser();
	let reservations = [];
	if (user) {
		return axios
			.post(`${process.env.FRONTEND_URL}/api/reservations`, {
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
			.post(`${process.env.FRONTEND_URL}/api/reservations`, {
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

export async function getReservationsByOwnerId(ownerId: string) {
	const user = await getCurrentUser();
	let reservations = [];
	if (user) {
		return axios
			.post(`${process.env.FRONTEND_URL}/api/reservations`, {
				customHeaders: user.customHeaders,
				ownerId,
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
		const { listingId, userId, ownerId } = params;

		const query: any = {};

		let reservationsData = [];

		if (listingId) {
			reservationsData = await getReservationsByListingId(listingId);
		}

		if (userId) {
			reservationsData = await getReservationsByUserId(userId);
		}

		if (ownerId) {
			reservationsData = await getReservationsByOwnerId(ownerId);
		}

		if (!listingId && !userId && !ownerId) {
			reservationsData = await getAllReservations();
		}

		return reservationsData;
	} catch (error: any) {
		throw new Error(error);
	}
}
