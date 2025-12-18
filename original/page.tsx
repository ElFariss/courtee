import { Header } from "@/components/header"
import { Footer } from "@/components/footer"
import { SearchHero } from "@/components/search-hero"
import { VenueCard } from "@/components/venue-card"

const venues = [
  {
    id: "longfield-sport-center",
    name: "Longfield Sport Center",
    type: "Lapangan Sepak Bola",
    location: "Jakarta Barat",
    price: "Rp 25.000/jam",
    image: "/soccer-field-green-grass-stadium.jpg",
    timeSlots: ["07.00-08.00", "08.00-09.00", "10.00-11.00", "13.00-14.00", "14.00-15.00"],
  },
  {
    id: "culture-padel",
    name: "Culture Padel",
    type: "Lapangan Padel",
    location: "Jakarta Barat",
    price: "Rp 40.000/jam",
    image: "/padel-tennis-court-indoor-blue.jpg",
    timeSlots: ["05.00-06.00", "08.00-09.00", "10.00-11.00", "13.00-14.00"],
  },
  {
    id: "balistic-badminton",
    name: "Balistic Badminton",
    type: "Lapangan Badminton",
    location: "Jakarta Barat",
    price: "Rp 35.000/jam",
    image: "/badminton-court-indoor-wooden-floor.jpg",
    timeSlots: ["07.00-08.00", "08.00-09.00", "10.00-11.00", "13.00-14.00", "14.00-15.00"],
  },
  {
    id: "sumber-sari-jaya",
    name: "Sumber Sari Jaya",
    type: "Lapangan Mini Soccer",
    location: "Jakarta Barat",
    price: "Rp 25.000/jam",
    image: "/mini-soccer-field-outdoor-palm-trees.jpg",
    timeSlots: ["07.00-08.00", "08.00-09.00", "10.00-11.00", "13.00-14.00", "14.00-15.00"],
  },
  {
    id: "singhasari-tennis-club",
    name: "Singhasari Tennis Club",
    type: "Lapangan Tennis",
    location: "Jakarta Barat",
    price: "Rp 40.000/jam",
    image: "/tennis-court-clay-orange-outdoor.jpg",
    timeSlots: ["05.00-06.00", "09.00-10.00", "10.00-11.00", "13.00-14.00"],
  },
  {
    id: "balistic-badminton-2",
    name: "Balistic Badminton",
    type: "Lapangan Badminton",
    location: "Jakarta Barat",
    price: "Rp 35.000/jam",
    image: "/badminton-court-indoor-green-floor.jpg",
    timeSlots: ["07.00-08.00", "08.00-09.00", "10.00-11.00", "13.00-14.00", "14.00-15.00"],
  },
]

export default function HomePage() {
  return (
    <div className="min-h-screen flex flex-col">
      <Header />
      <main className="flex-1">
        <SearchHero />
        <section className="container mx-auto px-4 py-12">
          <h2 className="text-2xl font-semibold text-[#9957B3] mb-8">Rekomendasi Venue</h2>
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {venues.map((venue) => (
              <VenueCard key={venue.id} {...venue} />
            ))}
          </div>
        </section>
      </main>
      <Footer />
    </div>
  )
}
