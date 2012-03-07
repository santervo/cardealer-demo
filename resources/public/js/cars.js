$(function() {

	window.CarsCollection = Backbone.Collection.extend({
		url: "/cars"
	});

	window.CarsTableView = Backbone.View.extend({
		el: $("#cars-table"),

		initialize: function() {
			this.collection.on("reset", this.resetView, this);
		},

		resetView: function() {
			this.collection.each(this.addCar, this);
		},

		addCar: function(car) {
			this.$el.dataTable().fnAddData(
				[car.get("model"),car.get("licenceNumber"),car.get("price")]);
		},

		render: function() {
			this.$el.dataTable({sDom: "ftlp"});
		}
	});
});
