const path = require('path');
const CopyPlugin = require("copy-webpack-plugin");
const Dotenv = require('dotenv-webpack');

// Get the name of the appropriate environment variable (`.env`) file for this build/run of the app
const dotenvFile = process.env.API_LOCATION ? `.env.${process.env.API_LOCATION}` : '.env';

module.exports = {
  plugins: [
    new CopyPlugin({
      patterns: [
        {
          from: "static_assets", to: "../",
          globOptions: {
            ignore: ["**/.DS_Store"],
          },
        },
      ],
    }),
    new Dotenv({ path: dotenvFile }),
  ],
  optimization: {
    usedExports: true
  },
  entry: {
    createOutfit: path.resolve(__dirname, 'src', 'pages', 'createOutfit.js'),
    viewOutfit: path.resolve(__dirname, 'src', 'pages', 'viewOutfit.js'),
    createClothing: path.resolve(__dirname, 'src', 'pages', 'createClothing.js'),
    viewClothing: path.resolve(__dirname, 'src', 'pages', 'viewClothing.js'),
    viewUserOutfits: path.resolve(__dirname, 'src', 'pages', 'viewUserOutfits.js'),
    viewUserClothing: path.resolve(__dirname, 'src', 'pages', 'viewUserClothing.js'),
    viewUserRecommendations: path.resolve(__dirname, 'src', 'pages', 'viewUserRecommendations.js'),
  },
  output: {
    path: path.resolve(__dirname, 'build', 'assets'),
    filename: '[name].js',
    publicPath: '/assets/'
  },
  devServer: {
    static: {
      directory: path.join(__dirname, 'static_assets'),
    },
    port: 8000,
    client: {
      // overlay shows a full-screen overlay in the browser when there are js compiler errors or warnings
      overlay: true,
    },
  }
}
