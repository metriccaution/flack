const path = require('path')
const HtmlWebpackPlugin = require('html-webpack-plugin')

module.exports = {
  // TODO - Source map
  entry: path.resolve(process.cwd(), 'app/main.js'),
  output: {
    path: path.resolve(process.cwd(), 'dist'),
    filename: 'static/[name].js'
  },
  plugins : [
    new HtmlWebpackPlugin({
      filename: 'index.html',
      inject: true,
      template: './app/index.html'
    })
  ],
  module: {
    loaders: [
      {
        test: /\.jsx?$/,
        exclude: /node_modules/,
        loader: 'babel-loader'
      },
      {
        test: /\.css$/,
        loader: "style-loader!css-loader"
      }
    ]
  }
}
