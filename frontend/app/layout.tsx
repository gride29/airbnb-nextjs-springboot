import ClientOnly from "./components/ClientOnly";
import RegisterModal from "./components/modals/RegisterModal";
import Navbar from "./components/navbar/Navbar";
import "./globals.css";
import { Nunito } from "next/font/google";
import ToasterProvider from "./providers/ToasterProvider";
import LoginModal from "./components/modals/LoginModal";
import getCurrentUser from "./actions/getCurrentUser";
import oAuthSignOut from "../pages/api/oAuthSignOut";

export const metadata = {
	title: "Airbnb",
	description: "Airbnb clone",
};

const font = Nunito({
	subsets: ["latin"],
}); // https://nextjs.org/docs/basic-features/font-optimization

export default async function RootLayout({
	children,
}: {
	children: React.ReactNode;
}) {
	const user = await getCurrentUser();

	return (
		<html lang="en">
			<body className={font.className}>
				<ClientOnly>
					<ToasterProvider />
					<LoginModal />
					<RegisterModal />
					<Navbar currentUser={user} />
				</ClientOnly>
				{children}
			</body>
		</html>
	);
}
