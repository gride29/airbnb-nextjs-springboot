import React from "react";
import getCurrentUser from "../actions/getCurrentUser";
import ClientOnly from "../components/ClientOnly";
import EmptyState from "../components/EmptyState";
import Container from "../components/Container";
import Heading from "../components/Heading";

enum UserRole {
	ADMIN = "ROLE_ADMIN",
	MODERATOR = "ROLE_MODERATOR",
	USER = "ROLE_USER",
}

const ProfilePage = async () => {
	const currentUser = await getCurrentUser();

	if (!currentUser) {
		return (
			<ClientOnly>
				<EmptyState title="Unauthorized" subtitle="Please login or register" />
			</ClientOnly>
		);
	}

	return (
		<ClientOnly>
			<Container>
				<Heading
					title="Profile"
					subtitle="Personal information about your account"
				/>
				<div className="mt-6 grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 2xl:grid-cols-6 gap-8">
					<div className="space-y-2">
						<div className="flex items-center space-x-1">
							<b>Username:</b>
							<p className="font-light text-neutral-500">
								{currentUser.username}
							</p>
						</div>
						<div className="flex items-center space-x-1">
							<b>Email:</b>
							<p className="font-light text-neutral-500">{currentUser.email}</p>
						</div>
						<div className="flex items-center space-x-1">
							<b>Role:</b>
							<p className="font-light text-neutral-500">
								{currentUser.roles
									? currentUser.roles[0] === UserRole.ADMIN
										? "Admin"
										: currentUser.roles[0] === UserRole.MODERATOR
										? "Moderator"
										: "User"
									: "User"}
							</p>
						</div>
					</div>
				</div>
			</Container>
		</ClientOnly>
	);
};

export default ProfilePage;
