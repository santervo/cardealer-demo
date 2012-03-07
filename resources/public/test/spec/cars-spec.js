var jsonResponse = function(data) {
	return [200,{"Content-Type": "application/json"}, JSON.stringify(data)];
};

var cars = [
	{ licenceNumber: "XYZ-123", model: "Honda Civic", price: "10000.0" },
	{ licenceNumber: "ABC-456", model: "Toyota Corolla", price: "5000.0" }
];

describe("CarsTableView", function() {

	beforeEach(function() {
		$("#cars-table tbody").children().remove();
		this.cars = new Backbone.Collection;
		this.view = new CarsTableView({collection: this.cars});
	});

	it("should enable dataTable on render", function() {
		this.view.$el.dataTable = sinon.mock();
		this.view.$el.dataTable.once();

		this.view.render();

		this.view.$el.dataTable.verify();
	});

	it("should add cars to dataTable on collection reset", function() {
		var fnAddData = sinon.spy();
		var dataTable = {fnAddData: fnAddData};
		this.view.$el.dataTable = function() { return dataTable; };

		this.cars.reset(cars);

		sinon.assert.calledWith(fnAddData, [cars[0].model, cars[0].licenceNumber, cars[0].price]);
		sinon.assert.calledWith(fnAddData, [cars[1].model, cars[1].licenceNumber, cars[1].price]);
	});
});

describe("CarsCollection", function() {
	describe("after fetch", function() {
		beforeEach(function() {
			this.server = sinon.fakeServer.create();
			this.server.respondWith("GET", "/cars", jsonResponse(cars));

			this.it = new CarsCollection;
			this.it.fetch();
			this.server.respond();
		});

		it("should contain correct cars", function() {
			expect(this.it.toJSON()).toEqual(cars);
		});
	});
});

