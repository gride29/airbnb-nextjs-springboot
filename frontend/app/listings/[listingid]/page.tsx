import getCurrentUser from "@/app/actions/getCurrentUser";
import { getListingById } from "@/app/actions/listingsActions";
import ClientOnly from "@/app/components/ClientOnly";
import EmptyState from "@/app/components/EmptyState";
import ListingClient from "./ListingClient";

interface IParams {
	listingid?: string;
}

const ListingPage = async ({ params }: { params: IParams }) => {
	const listing = await getListingById(params.listingid as string);
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
			<ListingClient listing={listing} currentUser={user} />
		</ClientOnly>
	);
};

export default ListingPage;
