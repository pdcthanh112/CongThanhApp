package main

import (
	"log"
	"net"
	"github.com/hashicorp/consul/api"
)

type ServiceRegistry struct {
	client *api.Client
}

func NewServiceRegistry() (*ServiceRegistry, error) {
	config := api.DefaultConfig()
	client, err := api.NewClient(config)
	if err != nil {
		return nil, err
	}
	return &ServiceRegistry{client: client}, nil
}

func (sr *ServiceRegistry) RegisterService(name, address string, port int) error {
	registration := &api.AgentServiceRegistration{
		Name:    name,
		Address: address,
		Port:    port,
		Check: &api.AgentServiceCheck{
			HTTP:     fmt.Sprintf("http://%s:%d/health", address, port),
			Interval: "10s",
			Timeout:  "5s",
		},
	}

	return sr.client.Agent().ServiceRegister(registration)
}

func (sr *ServiceRegistry) DiscoverService(name string) ([]*api.ServiceEntry, error) {
	entries, _, err := sr.client.Health().Service(name, "", true, nil)
	return entries, err
}

func main() {
	registry, err := NewServiceRegistry()
	if err != nil {
		log.Fatalf("Failed to create service registry: %v", err)
	}

	// Đăng ký các service
	services := []struct {
		Name    string
		Address string
		Port    int
	}{
		{"api-gateway", "localhost", 8080},
		{"auth-service", "localhost", 3000},
		{"ecommerce-service", "localhost", 8081},
	}

	for _, service := range services {
		if err := registry.RegisterService(service.Name, service.Address, service.Port); err != nil {
			log.Printf("Failed to register %s: %v", service.Name, err)
		}
	}

	listener, err := net.Listen("tcp", ":8500")
	if err != nil {
		log.Fatalf("Failed to start listener: %v", err)
	}
	defer listener.Close()

	log.Println("Service Discovery running on :8500")
	
	// Giữ cho chương trình chạy
	select {}
}