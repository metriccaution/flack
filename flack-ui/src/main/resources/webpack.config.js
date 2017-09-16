const path = require('path')

module.exports = {
  // TODO - Source map
  entry: path.resolve(process.cwd(), 'app/main.js'),
  output: {
    path: path.resolve(process.cwd(), 'app/bundle.js')
  },
  devServer: {
    inline: true,
    contentBase: path.resolve(process.cwd(), 'app'),
    port: 9731
  },
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
