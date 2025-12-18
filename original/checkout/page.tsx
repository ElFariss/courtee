"use client"

import { useState } from "react"
import Link from "next/link"
import { ArrowLeft, Building2 } from "lucide-react"
import { Button } from "@/components/ui/button"

const orderItems = [
  {
    venueName: "Longfield Sport Center",
    courtName: "Lapangan Makmur",
    date: "20 Desember 2025",
    time: "09.00-10.00",
    price: 25000,
  },
  {
    venueName: "Longfield Sport Center",
    courtName: "Lapangan Makmur",
    date: "20 Desember 2025",
    time: "10.00-11.00",
    price: 25000,
  },
]

export default function CheckoutPage() {
  const [selectedPayment, setSelectedPayment] = useState<"qris" | "mobile-banking">("qris")

  const subtotal = orderItems.reduce((acc, item) => acc + item.price, 0)
  const tax = subtotal * 0.12
  const total = subtotal + tax

  const formatCurrency = (amount: number) => {
    return new Intl.NumberFormat("id-ID", {
      style: "currency",
      currency: "IDR",
      minimumFractionDigits: 2,
    })
      .format(amount)
      .replace("IDR", "Rp")
  }

  return (
    <div className="min-h-screen bg-background">
      <div className="container mx-auto px-4 py-8 max-w-2xl">
        {/* Header - Updated to brand purple */}
        <div className="flex items-center gap-4 mb-8">
          <Link href="/venue/longfield-sport-center">
            <button className="w-10 h-10 rounded-lg bg-[#9957B3] flex items-center justify-center text-white hover:bg-[#874da0] transition-colors">
              <ArrowLeft className="h-5 w-5" />
            </button>
          </Link>
          <h1 className="text-2xl font-bold text-[#9957B3]">Detail Pemesanan</h1>
        </div>

        {/* Order Items */}
        <div className="space-y-4 mb-8">
          {orderItems.map((item, index) => (
            <div key={index} className="bg-[#f9fafb] rounded-lg p-5 border border-[#EAECF0]">
              <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4">
                <div>
                  <h3 className="font-semibold text-[#9957B3]">{item.venueName}</h3>
                  <p className="text-sm text-muted-foreground">{item.courtName}</p>
                </div>
                <div className="flex items-center gap-4 text-sm">
                  <span className="text-muted-foreground">{item.date}</span>
                  <span className="font-semibold text-[#9957B3]">{item.time}</span>
                  <span className="text-[#9957B3]">{formatCurrency(item.price)}</span>
                </div>
              </div>
            </div>
          ))}
        </div>

        {/* Summary - Updated to brand purple */}
        <div className="space-y-3 mb-8 px-4">
          <div className="flex justify-between text-sm">
            <span className="text-[#9957B3] font-medium">Subtotal</span>
            <span className="text-[#9957B3]">{formatCurrency(subtotal)}</span>
          </div>
          <div className="flex justify-between text-sm">
            <span className="text-[#9957B3] font-medium">Pajak (12%)</span>
            <span className="text-[#9957B3]">{formatCurrency(tax)}</span>
          </div>
          <div className="border-t border-[#9957B3]/30 pt-3 mt-3">
            <div className="flex justify-between font-semibold">
              <span className="text-[#9957B3]">Total</span>
              <span className="text-[#9957B3]">{formatCurrency(total)}</span>
            </div>
          </div>
        </div>

        {/* Payment Methods - Updated selection border to green */}
        <div className="mb-8">
          <h2 className="text-xl font-semibold text-[#9957B3] mb-4">Metode Pembayaran</h2>
          <div className="grid grid-cols-2 gap-4">
            <button
              onClick={() => setSelectedPayment("qris")}
              className={`p-6 rounded-lg border-2 transition-all ${
                selectedPayment === "qris"
                  ? "border-[#008733] bg-background"
                  : "border-[#EAECF0] bg-background hover:border-muted-foreground"
              }`}
            >
              <div className="flex items-center justify-center">
                <svg viewBox="0 0 100 40" className="h-12 w-auto">
                  <text x="10" y="30" fontFamily="Arial Black" fontSize="24" fontWeight="bold">
                    QRIS
                  </text>
                </svg>
              </div>
            </button>
            <button
              onClick={() => setSelectedPayment("mobile-banking")}
              className={`p-6 rounded-lg border-2 transition-all ${
                selectedPayment === "mobile-banking"
                  ? "border-[#008733] bg-background"
                  : "border-[#EAECF0] bg-background hover:border-muted-foreground"
              }`}
            >
              <div className="flex items-center justify-center gap-2">
                <Building2 className="h-8 w-8" />
                <div className="text-left">
                  <div className="font-bold text-sm">Mobile</div>
                  <div className="font-bold text-sm">Banking</div>
                </div>
              </div>
            </button>
          </div>
        </div>

        {/* Continue Button - Updated to brand purple */}
        <Link href={selectedPayment === "qris" ? "/payment/qris" : "/payment/mobile-banking"}>
          <Button className="w-full py-6 text-base font-medium bg-[#9957B3] hover:bg-[#874da0] text-white rounded-lg">
            Lanjutkan ke Konfirmasi Pembayaran
          </Button>
        </Link>
      </div>
    </div>
  )
}
