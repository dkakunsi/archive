FROM golang:1.16-alpine

WORKDIR /app

COPY go.mod .
COPY go.sum .
COPY main.go .

RUN go mod download && CGO_ENABLED=0 GOOS=linux go build -a -installsuffix cgo -o app .




FROM alpine:latest  

RUN apk --no-cache add ca-certificates
WORKDIR /myapp
COPY --from=0 app ./

EXPOSE 4000

CMD ["./app"]
