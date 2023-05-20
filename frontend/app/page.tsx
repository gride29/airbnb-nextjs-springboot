import getCurrentUser from "./actions/getCurrentUser";
import { getListings, getListingById } from "./actions/listingsActions";
import ClientOnly from "./components/ClientOnly";
import Container from "./components/Container";
import EmptyState from "./components/EmptyState";
import ListingCard from "./components/listings/ListingCard";

export default async function Home() {
	const user = await getCurrentUser();
	const listings = await getListings();

	if (!listings) {
		return (
			<ClientOnly>
				<EmptyState
					title="Unauthorized"
					subtitle="Please log in or register to view listings"
				/>
			</ClientOnly>
		);
	}

	return (
		<ClientOnly>
			<Container>
				<div className="pt-24 grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 2xl:grid-cols-6 gap-8">
					{listings.map((listing: any) => {
						return (
							<ListingCard key={listing.id} currentUser={user} data={listing} />
						);
					})}
				</div>
			</Container>
		</ClientOnly>
	);
}
