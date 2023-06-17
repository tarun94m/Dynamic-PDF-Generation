# Dynamic-PDF-Generation
Freightfox Assignment for Dynamic PDF Generation

Created Controller: where post mapping is implemented to generate PDF

Created Model: where item and order classes are created. 
Items hold the details of the product and the order holds the details of the seller and buyer.

Created Service: Two classes are created for PDF generator and local storage
In pdf generator service class:
1. Create HTML using Thymeleaf template Engine
2. Setup Source and target I/O streams
3. Call convert method

In local storage service class:
1. created methods to store, get and store order

Created Order HTML: This holds the design of the pdf template.

Test Cases: Created test case for the controller and service
