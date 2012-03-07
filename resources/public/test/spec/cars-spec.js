var jsonResponse = function(data) {
	return [200,{"Content-Type": "application/json"}, JSON.stringify(data)];
};

describe("CarsTableView", function() {

	var cars = [
		{ licenceNumber: "XYZ-123", model: "Honda Civic", price: "10000.0" },
		{ licenceNumber: "ABC-456", model: "Toyota Corolla", price: "5000.0" }
	];

	beforeEach(function() {
		$("#cars-table tbody").children().remove();
		this.server = sinon.fakeServer.create();
		this.server.respondWith("GET", "/cars", jsonResponse(cars));
		this.view = new CarsTableView();
		this.server.respond();
	});

	describe("initially", function() {
		it("should bind to cars-table", function() {
			expect(this.view.el.id).toEqual("cars-table");
		});
		it("should add initial car", function() {
			expect($("#cars-table tbody").children().size()).toEqual(2);
		});
	});
});
