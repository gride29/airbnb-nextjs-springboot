"use client";

import Container from "@/app/components/Container";
import ListingHead from "@/app/components/listings/ListingHead";
import ListingInfo from "@/app/components/listings/ListingInfo";
import ListingReservation from "@/app/components/listings/ListingReservation";
import { categories } from "@/app/components/navbar/Categories";
import useLoginModal from "@/app/hooks/useLoginModal";
import axios from "axios";
import { addDays, differenceInCalendarDays, eachDayOfInterval } from "date-fns";
import { useRouter } from "next/navigation";
import { useCallback, useEffect, useMemo, useState } from "react";
import { toast } from "react-hot-toast";

const initialDateRange = {
	startDate: new Date(),
	endDate: new Date(),
	key: "selection",
};

interface ListingClientProps {
	reservations?: any;
	listing?: any;
	currentUser?: any;
}

const ListingClient: React.FC<ListingClientProps> = ({
	listing,
	reservations = [],
	currentUser,
}) => {
	const loginModal = useLoginModal();
	const router = useRouter();

	const disabledDates = useMemo(() => {
		let dates: Date[] = [];

		reservations.forEach((reservation: any) => {
			const range = eachDayOfInterval({
				start: new Date(reservation.startDate),
				end: new Date(reservation.endDate),
			});

			dates = [...dates, ...range];
		});

		return dates;
	}, [reservations]);

	const [isLoading, setIsLoading] = useState(false);
	const [totalPrice, setTotalPrice] = useState(listing.price);
	const [dateRange, setDateRange] = useState(initialDateRange);

	const onCreateReservation = useCallback(async () => {
		if (!currentUser) {
			return loginModal.onOpen();
		}

		setIsLoading(true);

		const customData = {
			reservationData: {
				totalPrice,
				startDate: addDays(dateRange.startDate, 1),
				endDate: addDays(dateRange.endDate, 1),
				userId: currentUser?.id,
				listingId: listing?.id,
			},
			customHeaders: currentUser.customHeaders,
		};

		axios
			.post("/api/reservations", customData)
			.then(() => {
				toast.success("Reservation created successfully!");
				setDateRange(initialDateRange);
				router.push("/trips");
				router.refresh();
			})
			.catch(() => {
				toast.error("Something went wrong");
			})
			.finally(() => {
				setIsLoading(false);
			});
	}, [currentUser, totalPrice, dateRange, listing?.id, router, loginModal]);

	useEffect(() => {
		if (dateRange.startDate && dateRange.endDate) {
			const dayCount = differenceInCalendarDays(
				addDays(dateRange.endDate, 1),
				addDays(dateRange.startDate, 1)
			);

			if (dayCount && listing.price) {
				setTotalPrice(dayCount * listing.price);
			} else {
				setTotalPrice(listing.price);
			}
		}
	}, [dateRange, listing.price]);

	const category = useMemo(() => {
		return categories.find((item) => item.label === listing.category);
	}, [listing.category]);

	console.log(listing, "listingOnListingClient");

	return (
		<Container>
			<div className="max-w-screen-lg mx-auto">
				<div className="flex flex-col gap-6">
					<ListingHead
						title={listing.title}
						imageSrc={listing.imageSrc}
						location={listing.location}
						id={listing.id}
						currentUser={currentUser}
					/>
					<div className="grid grid-cols-1 md:grid-cols-7 md:gap-10 mt-6">
						<ListingInfo
							user={currentUser}
							category={category}
							description={listing.description}
							roomCount={listing.roomCount}
							guestCount={listing.guestCount}
							bathroomCount={listing.bathroomCount}
							locationValue={listing.location}
						/>
						<div className="order-first mb-10 md:order-last md:col-span-3">
							<ListingReservation
								price={listing.price}
								totalPrice={totalPrice}
								onChangeDate={(value: any) => setDateRange(value)}
								dateRange={dateRange}
								onSubmit={onCreateReservation}
								disabled={isLoading}
								disabledDates={disabledDates}
							/>
						</div>
					</div>
				</div>
			</div>
		</Container>
	);
};

export default ListingClient;
