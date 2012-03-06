$(function() {

	window.CarsCollection = Backbone.Collection.extend({
		url: "/cars"
	});

	window.CarsTableView = Backbone.View.extend({
		el: $("#cars-table"),

		initialize: function() {
			this.cars = new CarsCollection;
			this.cars.on("reset", this.resetView, this);
			this.cars.fetch();
		},

		resetView: function() {
			this.cars.each(this.addCar);
		},

		addCar: function(car) {
			$("tbody", this.el).append("<tr></tr>");
		}
	});
});
