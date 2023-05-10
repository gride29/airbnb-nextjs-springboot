import axios from "axios";
import getCurrentUser from "./getCurrentUser";

export default async function getListings() {
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
