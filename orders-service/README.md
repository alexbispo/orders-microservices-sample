# Orders Service

### Business Rules

* The Order total value should be equals to the 
sum of each item unit price times the requested quantity.

* The Order Item amount should be, multiply unit price by quantity. 

### Application Rules

- [ ] Receives the Order request via HTTP.

- [x] Chek the Order user in the database.

- [x] Check all Order products in the database.

- [x] Check the Order products available quantity.

- [x] Check the Order total value.

- [x] Decrease the products available quantity.

- [x] Saves the new Order.

- [ ] Returns the Order id on the HTTP response.

- [ ] Trigger Order Created Event.

- [ ] Listen the Product Changed Event to keep product updated.

- [ ] Listen User Changed Event to keep user updated.
