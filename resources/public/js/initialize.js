$(function() {
	window.carsCollection = new CarsCollection;
	window.carsTableView = new CarsTableView({collection: carsCollection});
	window.carsCollection.fetch();
	window.carsTableView.render();
});
