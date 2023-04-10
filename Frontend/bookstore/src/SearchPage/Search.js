import BookTable from "../components/BookTable";
import { useEffect } from "react";
import { environment } from "../Environment";

const Search = ({ search, type }) => {
	let url = "";
	if (type === "Title") {
		url = "searchByTitle";
	} else if (type === "author") {
		url = "searchByAuthor";
	} else if (type === "category") {
		url = "searchByCategory";
	} else if (type === "ISBN") {
		url = "searchByISBN";
	} else if (type === "publisher") {
		url = "searchByPublisher";
	} else if (type === "price") {
		url = "searchByCategory";
	}
	//get some Books from the stock
	let res = null;
	useEffect(() => {
		async function getBooks() {
			let result = await fetch(`${environment.env}/${url}/${search}`, {
				method: "get",
				headers: {
					"Content-type": "application/json",
				},
			});
			res = await result.json();
		}
		getBooks();
	}, []);
	return (
		<>
			<BookTable books={res} />
		</>
	);
};

export default Search;
