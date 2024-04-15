Feature: Amazon add to card testcases

@Smoke
Scenario: Add items to cart and verify the price
Given I login to Amazon page
When I search for "<Item>" and press enter
And select the "<number>" item in the list
And Add the item to cart by clicking on add to cart
And open cart from the top-left
Then Verify that the price is identical to the product page
Then Verify that the sub total is identical to the product page
Examples:
|Item|number|
|Monitor|1|
|Laptop|2|

Scenario: Add items to cart and verify the price
Given I login to Amazon page
When I search for "Headphones" and press enter
And select the "1" item in the list
And Add the item to cart by clicking on add to cart
When I search for "Keyboard" and press enter
And select the "1" item in the list
And Add the item to cart by clicking on add to cart
And open cart from the top-left
Then Verify that the price is identical to the product page
Then Verify that the sub total is identical to the product page