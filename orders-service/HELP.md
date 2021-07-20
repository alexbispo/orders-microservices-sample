# Orders Service

### Business Rules

* The Order total value should be equals to the 
sum of each item unit price times the requested quantity.

* The Order Item amount should be, multiply unit price by quantity. 

### Application Rules

* The system receives the Order

* Is there in database the Order user?

* Are there in database all the Order items?

* Is the Order items quantity available?

* Is correct the Order total value?

* Saves the new Order

* Trigger Order Created Event.
