/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        'uts-verde': '#9BC31C',
        'uts-verde-oscuro': '#7A9E14',
      }
    },
  },
  plugins: [],
}