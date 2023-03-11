# JavaSpringRESTAPI
 Spring REST API Maven with H2 in-mem database
## Requirements
Java 17 SDK
## Download
[Latest release](https://github.com/sandy6078/JavaSpringRESTAPI/releases/tag/0.0.1)
## Tests
### GET (all)
![get_all_books](screenshots/get_all_books.png)
<br>
Getting all books with `GET` at `localhost:8080`
<br>
<br>
![get_all_books_raw](screenshots/get_all_books_raw.png)
Here is the raw output to prove all books have been fetched
<br>
### GET (one)
![get_book_5](screenshots/get_book_5.png)
Getting a single book `5` with `GET` at `localhost:8080`
<br>
### POST
![post_book](screenshots/post_book.png)
`POST` at `localhost:8080` showing an addition of a new book
<br>
### PUT
![put_customer_order_3](screenshots/put_customer_order_3.png)
Updating a customer order `3` with the status `cancelled`
<br>
### DELETE
![delete_book_15](screenshots/delete_book_15.png)
<br>
Deleting book `5` and the return body containing the book deleted
<br>
<br>
![delete_book_15_2](screenshots/delete_book_15_2.png)
<br>
An attempt to delete an non existing book
