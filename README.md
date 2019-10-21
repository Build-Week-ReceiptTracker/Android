# Android

- Use MVVM architecture: Room DB, Retrofit, Single Activity with fragments design pattern.
- Login will be separate activity so we can use conductor instead
- Main screen after log in, nav drawer or options menu to launch fragments
	- Fragments
		- Add receipt
		- Search receipts
			- recyclerview to show all receipts and search bar to filter, with options to search merchant, date, amount, category of purchase
			- clicking on recycler will either expand card to show details or launch fragment with details SCUD functionality
			- take picture to attach to receipt, as well searching photos

	- Stretch
		- Use ARCore/MLkit to scan receipts and automatically populate data
		- Use some Graph API to display info

- Other
	- MediaPlayer
	- UnitTesting
	- Geocoder for receipt location if time
	- Use Android Nav Component

- Libraries
	- recyclerview
	- retrofit
	- cardview
	- room
	- conductor
	- livedata
	- Maybe Dagger?
	- Google Maps if time
