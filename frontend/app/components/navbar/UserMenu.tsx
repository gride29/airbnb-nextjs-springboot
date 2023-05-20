import { AiOutlineMenu } from "react-icons/ai";
import Avatar from "../Avatar";
import { useCallback, useEffect, useState } from "react";
import MenuItem from "./MenuItem";
import useRegisterModal from "@/app/hooks/useRegisterModal";
import useLoginModal from "@/app/hooks/useLoginModal";
import { signOut } from "next-auth/react";
import { useRouter } from "next/navigation";
import { BiLogInCircle } from "react-icons/bi";
import useRentModal from "@/app/hooks/useRentModal";

interface UserMenuProps {
	currentUser?: any | null;
}

const UserMenu: React.FC<UserMenuProps> = ({ currentUser }) => {
	const router = useRouter();
	const rentModal = useRentModal();
	const registerModal = useRegisterModal();
	const loginModal = useLoginModal();
	const [isOpen, setIsOpen] = useState(false);

	// Without useCallback, toggleOpen would be recreated on every render
	const toggleOpen = useCallback(() => {
		setIsOpen((prev) => !prev);
	}, []);

	const handleCustomSignOut = async () => {
		if (currentUser.isOAuthUser) {
			const res = await fetch(`/api/oAuthSignOut`);
			router.refresh();
		} else {
			signOut();
		}
	};

	const onRent = useCallback(() => {
		if (!currentUser) {
			loginModal.onOpen();
		} else {
			rentModal.onOpen();
		}
	}, [currentUser, loginModal]);

	const handleClickOutside = useCallback((event: MouseEvent) => {
		const target = event.target as HTMLElement;
		const menuElement = document.getElementById("user-menu");

		if (
			menuElement &&
			!menuElement.contains(target) &&
			!target.closest(".user-menu-trigger")
		) {
			setTimeout(() => {
				setIsOpen(false);
			}, 100); // Delay the closing of the menu to handle the subsequent click
		}
	}, []);

	useEffect(() => {
		document.addEventListener("mousedown", handleClickOutside);
		return () => {
			document.removeEventListener("mousedown", handleClickOutside);
		};
	}, [handleClickOutside]);

	return (
		<div className="relative">
			<div className="flex flex-row items-center gap-2">
				<div
					onClick={onRent}
					className="hidden md:block text-sm font-semibold py-3 px-4 rounded-full hover:bg-netural-100 transition cursor-pointer hover:shadow-md"
				>
					Rent your place on Airbnb
				</div>
				<div
					onClick={toggleOpen}
					id="user-menu"
					className="p-4 md:py-1 md:px-2 border-[1px] border-neutral-200 flex flex-row items-center gap-3 rounded-full cursor-pointer hover:shadow-md transition"
				>
					<AiOutlineMenu />
					<div className="hidden md:block">
						{currentUser ? (
							<Avatar src={currentUser?.avatarUrl} />
						) : (
							<BiLogInCircle size={28} />
						)}
					</div>
				</div>
			</div>

			{isOpen && (
				<div className="absolute rounded-xl shadow-md w-[40vw] md:w-3/4 bg-white overflow-hidden right-0 top-12 text-sm">
					<div className="flex flex-col cursor-pointer">
						{currentUser ? (
							<>
								<MenuItem
									label="My trips"
									onClick={() => router.push("/trips")}
								/>
								<MenuItem
									label="My favorites"
									onClick={() => {
										router.push("/favorites");
									}}
								/>
								<MenuItem
									label="My reservations"
									onClick={() => {
										router.push("/reservations");
									}}
								/>
								<MenuItem
									label="My properties"
									onClick={() => {
										router.push("/properties");
									}}
								/>
								<MenuItem
									label="Rent your place on Airbnb"
									onClick={rentModal.onOpen}
								/>
								<hr />
								<MenuItem
									label="Logout"
									onClick={() => handleCustomSignOut()}
								/>
							</>
						) : (
							<>
								<MenuItem label="Login" onClick={loginModal.onOpen} />
								<MenuItem label="Sign up" onClick={registerModal.onOpen} />
							</>
						)}
					</div>
				</div>
			)}
		</div>
	);
};

export default UserMenu;
