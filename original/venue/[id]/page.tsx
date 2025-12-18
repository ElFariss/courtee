"use client"

import { useState } from "react"
import Image from "next/image"
import Link from "next/link"
import { Header } from "@/components/header"
import { CourtCard } from "@/components/court-card"
import { ChevronDown, Instagram, Share, MapPin } from "lucide-react"
import { Button } from "@/components/ui/button"

const venueData = {
  name: "Longfield Sport Center",
  location: "Jakarta Pusat",
  heroImage: "/soccer-field-aerial-view-green-grass-stadium.jpg",
  courts: [
    {
      id: "lapangan-sejahtera",
      name: "Lapangan Sejahtera",
      description:
        "Lapangan rumput sintetis berkualitas dengan penerangan malam, gawang standar, dan area bersih. Cocok untuk latihan, pertandingan persahabatan, serta kegiatan komunitas.",
      image: "/indoor-soccer-field-green-artificial-turf.jpg",
      timeSlots: [
        { time: "06.00-07.00", price: "Rp 24.000,00", available: true },
        { time: "07.00-08.00", price: "Rp 24.000,00", available: true },
        { time: "09.00-10.00", price: "Rp 24.000,00", available: true },
        { time: "10.00-11.00", price: "Rp 24.000,00", available: false },
        { time: "11.00-12.00", price: "Rp 24.000,00", available: false },
        { time: "13.00-14.00", price: "Rp 24.000,00", available: false },
        { time: "14.00-15.00", price: "Rp 24.000,00", available: true },
        { time: "16.00-17.00", price: "Rp 24.000,00", available: true },
      ],
    },
    {
      id: "lapangan-makmur",
      name: "Lapangan Makmur",
      description:
        "Lapangan rumput sintetis berkualitas dengan penerangan malam, gawang standar, dan area bersih. Cocok untuk latihan, pertandingan persahabatan, serta kegiatan komunitas.",
      image: "/indoor-futsal-court-green-floor-bright-lights.jpg",
      timeSlots: [
        { time: "06.00-07.00", price: "Rp 25.000,00", available: true },
        { time: "07.00-08.00", price: "Rp 25.000,00", available: true },
        { time: "09.00-10.00", price: "Rp 25.000,00", available: true },
        { time: "10.00-11.00", price: "Rp 24.000,00", available: false },
        { time: "11.00-12.00", price: "Rp 24.000,00", available: false },
        { time: "13.00-14.00", price: "Rp 24.000,00", available: false },
        { time: "14.00-15.00", price: "Rp 24.000,00", available: true },
        { time: "16.00-17.00", price: "Rp 24.000,00", available: true },
      ],
    },
  ],
}

interface SelectedSlot {
  courtId: string
  courtName: string
  time: string
  price: string
}

export default function VenueDetailPage() {
  const [selectedDate, setSelectedDate] = useState("20 Desember 2025")
  const [selectedSlots, setSelectedSlots] = useState<SelectedSlot[]>([])

  const handleSlotSelect = (
    courtId: string,
    courtName: string,
    slot: { time: string; price: string; available: boolean },
  ) => {
    setSelectedSlots((prev) => {
      const existingIndex = prev.findIndex((s) => s.courtId === courtId && s.time === slot.time)
      if (existingIndex > -1) {
        // Remove if already selected
        return prev.filter((_, index) => index !== existingIndex)
      } else {
        // Add new selection
        return [...prev, { courtId, courtName, time: slot.time, price: slot.price }]
      }
    })
  }

  const getSelectedTimesForCourt = (courtId: string): string[] => {
    return selectedSlots.filter((s) => s.courtId === courtId).map((s) => s.time)
  }

  const hasSelectedSlots = selectedSlots.length > 0

  return (
    <div className="min-h-screen flex flex-col bg-background">
      <Header />
      <main className="flex-1">
        {/* Hero Image */}
        <div className="relative h-[250px] w-full">
          <Image src={venueData.heroImage || "/placeholder.svg"} alt={venueData.name} fill className="object-cover" />
        </div>

        <div className="container mx-auto px-4 py-8">
          {/* Venue Info */}
          <div className="mb-8">
            <h1 className="text-4xl font-bold text-foreground tracking-tight" style={{ letterSpacing: "-0.72px" }}>
              {venueData.name}
            </h1>
            <p className="text-muted-foreground mt-1">{venueData.location}</p>
            <div className="flex gap-2 mt-4">
              <button className="w-9 h-9 rounded-full bg-[#9957B3] flex items-center justify-center text-white hover:bg-[#874da0] transition-colors">
                <Instagram className="h-4 w-4" />
              </button>
              <button className="w-9 h-9 rounded-full bg-[#9957B3] flex items-center justify-center text-white hover:bg-[#874da0] transition-colors">
                <Share className="h-4 w-4" />
              </button>
              <button className="w-9 h-9 rounded-full bg-[#008733] flex items-center justify-center text-white hover:bg-[#00722b] transition-colors">
                <MapPin className="h-4 w-4" />
              </button>
            </div>
          </div>

          {/* Date Picker */}
          <div className="mb-8">
            <div className="relative w-64">
              <select
                value={selectedDate}
                onChange={(e) => setSelectedDate(e.target.value)}
                className="w-full px-4 py-2.5 rounded-lg border border-[#EAECF0] bg-background text-foreground appearance-none focus:outline-none focus:ring-2 focus:ring-[#9957B3] cursor-pointer text-sm"
              >
                <option value="">Tentukan Tanggal</option>
                <option>20 Desember 2025</option>
                <option>21 Desember 2025</option>
                <option>22 Desember 2025</option>
              </select>
              <ChevronDown className="absolute right-3 top-1/2 -translate-y-1/2 h-4 w-4 text-muted-foreground pointer-events-none" />
            </div>
          </div>

          {/* Available Courts */}
          <section>
            <h2 className="text-xl font-bold text-foreground mb-6">Lapangan yang Tersedia</h2>
            <div className="space-y-6">
              {venueData.courts.map((court) => (
                <CourtCard
                  key={court.id}
                  courtId={court.id}
                  name={court.name}
                  description={court.description}
                  image={court.image}
                  timeSlots={court.timeSlots}
                  onSlotSelect={(slot) => handleSlotSelect(court.id, court.name, slot)}
                  selectedSlots={getSelectedTimesForCourt(court.id)}
                />
              ))}
            </div>
          </section>

          <div className="mt-8">
            {hasSelectedSlots ? (
              <Link href="/checkout">
                <Button className="w-full py-6 text-base font-medium bg-[#9957B3] hover:bg-[#874da0] text-white rounded-lg">
                  Lanjutkan ke Pembayaran
                </Button>
              </Link>
            ) : (
              <Button
                disabled
                className="w-full py-6 text-base font-medium bg-muted text-muted-foreground rounded-lg cursor-not-allowed"
              >
                Lanjutkan ke Pembayaran
              </Button>
            )}
          </div>
        </div>
      </main>
    </div>
  )
}
