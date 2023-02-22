import express from 'express'
import fetch from 'node-fetch'
import { Seat } from './seat'

const app = express()
app.use(express.json())
const port = 8083


app.post("/reserve", async (req, res) => {
    const { body } = req
    const seatCount = body.count
    const trainId = body.train_id

    // Step 1: get a booking reference
    let response = await fetch("http://localhost:8082/booking_reference")
    const bookingReference = await response.text()

    // Step 2: fetch train data
    response = await fetch(`http://localhost:8081/data_for_train/${trainId}`)
    const train = await response.json()
    const seatsInTrain: Seat[] = Object.values(train.seats)

    // Step 3: find available seats
    const availableSeats = seatsInTrain.filter(s => s.booking_reference === "")
    // TODO: do not hard-code coach number
    const inFirstCoach = availableSeats.filter(s => s.coach === "A")

    // Step 4: make reservation
    const toReserve = inFirstCoach.slice(0, seatCount)
    const seatIds = toReserve.map(s => `${s.seat_number}${s.coach}`)
    const reservation = {
        booking_reference: bookingReference,
        seats: seatIds,
        train_id: trainId
    }
    response = await fetch(`http://localhost:8081/reserve`, {
        method: 'POST',
        body: JSON.stringify(reservation),
        headers: { 'Content-Type': 'application/json' }
    })
    const status = response.status
    if (status != 200) {
        res.status(500)
        const message = await response.text()
        res.send(message)
        return
    }

    // Step 5: send back the reservation that was made
    res.send(reservation)
})

app.listen(port, () => {
    console.log(`Ticket Office listening on port ${port}`)
}) 