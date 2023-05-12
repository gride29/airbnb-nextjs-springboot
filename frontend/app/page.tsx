import getCurrentUser from "./actions/getCurrentUser";
import { getListings, getListingById } from "./actions/listingsActions";
import ClientOnly from "./components/ClientOnly";
import Container from "./components/Container";
import EmptyState from "./components/EmptyState";
import ListingCard from "./components/listings/ListingCard";

export default async function Home() {
	const user = await getCurrentUser();
	const listings = await getListings();

	// const listing = await getListingById("645c1b4d73e186643c9019d5");
	// console.log("fetched listing", listing);

	if (!listings) {
		return (
			<ClientOnly>
				<EmptyState showReset />
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
