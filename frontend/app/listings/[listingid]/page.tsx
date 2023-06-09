import getCurrentUser from "@/app/actions/getCurrentUser";
import { getListingById } from "@/app/actions/listingsActions";
import ClientOnly from "@/app/components/ClientOnly";
import EmptyState from "@/app/components/EmptyState";
import ListingClient from "./ListingClient";
import getReservations from "@/app/actions/getReservations";

interface IParams {
	listingId?: string;
}

const ListingPage = async ({ params }: { params: IParams }) => {
	const listing = await getListingById("");
	const reservations = await getReservations({ listingId: "" });
	const user = await getCurrentUser();

	if (!listing) {
		return (
			<ClientOnly>
				<EmptyState />
			</ClientOnly>
		);
	}

	return (
		<ClientOnly>
			<ListingClient
				listing={listing}
				reservations={reservations}
				currentUser={user}
			/>
		</ClientOnly>
	);
};

export default ListingPage;
