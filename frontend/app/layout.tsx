import ClientOnly from "./components/ClientOnly";
import Modal from "./components/modals/Modal";
import RegisterModal from "./components/modals/RegisterModal";
import Navbar from "./components/navbar/Navbar";
import "./globals.css";
import { Nunito } from "next/font/google";
import ToasterProvider from "./providers/ToasterProvider";

export const metadata = {
	title: "Airbnb",
	description: "Airbnb clone",
};

const font = Nunito({
	subsets: ["latin"],
}); // https://nextjs.org/docs/basic-features/font-optimization

export default function RootLayout({
	children,
}: {
	children: React.ReactNode;
}) {
	return (
		<html lang="en">
			<body className={font.className}>
				<ClientOnly>
					<ToasterProvider />
					<RegisterModal />
					<Navbar />
				</ClientOnly>
				{children}
			</body>
		</html>
	);
}
