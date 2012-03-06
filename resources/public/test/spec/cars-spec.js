describe("CarsTableView", function() {
	beforeEach(function() {
		$("#cars-table tbody").children().remove();
		this.server = sinon.fakeServer.create();
		this.server.respondWith(
			"GET",
			"/cars",
			[200,{"Content-Type": "application/json"},
			'[{"licenceNumber": "XYZ-123", "model": "Honda Civic", "price": "123.50"}]']);
		this.view = new CarsTableView();
		this.server.respond();
	});

	describe("initially", function() {
		it("should bind to cars-table", function() {
			expect(this.view.el.id).toEqual("cars-table");
		});
		it("should add initial car", function() {
			expect($("#cars-table tbody").children().size()).toEqual(1);
		});
	});
});
